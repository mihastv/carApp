import 'dart:convert';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;
import 'package:path_provider/path_provider.dart';
import 'package:pdf/widgets.dart' as pw;
import 'services/download_service.dart';

const String apiBaseUrl = 'http://localhost:8083';
const String lmStudioBaseUrl = 'http://localhost:1234';
const String lmStudioModel = 'Lfm2.5 1.2B';

const List<String> engineOptions = [
  'V4',
  'V6',
  'V8',
  'ELECTRIC',
  'HYBRID',
];

const List<String> transmissionOptions = [
  'MANUAL_5_SPEED',
  'MANUAL_6_SPEED',
  'AUTOMATIC_6_SPEED',
  'AUTOMATIC_8_SPEED',
  'CVT',
  'DUAL_CLUTCH',
];

const List<String> exportFormats = [
  'pdf',
  'word',
  'html',
  'markdown',
];

void main() {
  runApp(const CarEditorApp());
}

class CarEditorApp extends StatelessWidget {
  const CarEditorApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Car Configurator',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.teal),
        useMaterial3: true,
      ),
      home: const CarConfigPage(),
    );
  }
}

class CarConfigPage extends StatefulWidget {
  const CarConfigPage({super.key});

  @override
  State<CarConfigPage> createState() => _CarConfigPageState();
}

class _CarConfigPageState extends State<CarConfigPage> {
  final _formKey = GlobalKey<FormState>();
  final _customerController = TextEditingController();
  final _modelController = TextEditingController();

  String _engine = engineOptions.first;
  String _transmission = transmissionOptions.first;
  bool _submitting = false;
  Map<String, dynamic>? _lastOrder;
  String? _statusMessage;
  String? _promoText;
  bool _promoGenerating = false;

  @override
  void dispose() {
    _customerController.dispose();
    _modelController.dispose();
    super.dispose();
  }

  Future<void> _submitOrder() async {
    if (!_formKey.currentState!.validate()) {
      return;
    }

    setState(() {
      _submitting = true;
      _statusMessage = null;
    });

    final payload = {
      'customerName': _customerController.text.trim(),
      'model': _modelController.text.trim(),
      'engine': _engine,
      'transmission': _transmission,
    };

    try {
      final response = await http.post(
        Uri.parse('$apiBaseUrl/api/orders/custom'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode(payload),
      );

      if (response.statusCode >= 200 && response.statusCode < 300) {
        final data = jsonDecode(response.body) as Map<String, dynamic>;
        setState(() {
          _lastOrder = data;
          _statusMessage = 'Order created: ${data['orderId']}';
        });
      } else {
        setState(() {
          _statusMessage = 'Failed to create order (${response.statusCode}).';
        });
      }
    } catch (error) {
      setState(() {
        _statusMessage = 'Error: $error';
      });
    } finally {
      setState(() {
        _submitting = false;
      });
    }
  }

  Future<void> _export(String format) async {
    if (_lastOrder == null) {
      return;
    }

    final summary = _buildOrderSummary(_lastOrder!);
    final fileName = _buildFileName(_lastOrder!, format);
    String? filePath;

    if (format == 'pdf') {
      final bytes = await _buildPdf(summary);
      if (kIsWeb) {
        await downloadBytes(fileName, bytes);
      } else {
        final directory = await getApplicationDocumentsDirectory();
        filePath = '${directory.path}${Platform.pathSeparator}$fileName';
        await File(filePath).writeAsBytes(bytes);
      }
    } else if (format == 'word') {
      final rtf = _buildRtf(summary);
      if (kIsWeb) {
        await downloadText(fileName, rtf);
      } else {
        final directory = await getApplicationDocumentsDirectory();
        filePath = '${directory.path}${Platform.pathSeparator}$fileName';
        await File(filePath).writeAsString(rtf);
      }
    } else if (format == 'html') {
      final html = _buildHtml(summary);
      if (kIsWeb) {
        await downloadText(fileName, html);
      } else {
        final directory = await getApplicationDocumentsDirectory();
        filePath = '${directory.path}${Platform.pathSeparator}$fileName';
        await File(filePath).writeAsString(html);
      }
    } else {
      final markdown = _buildMarkdown(summary);
      if (kIsWeb) {
        await downloadText(fileName, markdown);
      } else {
        final directory = await getApplicationDocumentsDirectory();
        filePath = '${directory.path}${Platform.pathSeparator}$fileName';
        await File(filePath).writeAsString(markdown);
      }
    }

    if (!mounted) {
      return;
    }

    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(
          kIsWeb ? 'Download started.' : 'Exported to $filePath',
        ),
      ),
    );
  }

  String _buildFileName(Map<String, dynamic> order, String format) {
    final orderId = order['orderId'] ?? 'order';
    if (format == 'word') {
      return '$orderId.rtf';
    }
    if (format == 'markdown') {
      return '$orderId.md';
    }
    return '$orderId.$format';
  }

  String _buildOrderSummary(Map<String, dynamic> order) {
    final car = order['car'] as Map<String, dynamic>? ?? {};
    final features = _formatList(car['interiorFeatures']);
    final exterior = _formatList(car['exteriorOptions']);
    final safety = _formatList(car['safetyFeatures']);

    final promoSection = (_promoText == null || _promoText!.trim().isEmpty)
      ? ''
      : '\nPromo:\n${_promoText!.trim()}\n';

    return '''
Order ID: ${order['orderId'] ?? '-'}
Customer: ${order['customerName'] ?? '-'}
Status: ${order['status'] ?? '-'}
Order Date: ${order['orderDate'] ?? '-'}

Car VIN: ${car['vin'] ?? '-'}
Model: ${car['model'] ?? '-'}
Model Year: ${car['modelYear'] ?? '-'}
Engine: ${car['engine'] ?? '-'}
Transmission: ${car['transmission'] ?? '-'}
Color: ${car['color'] ?? '-'}
Rims: ${car['rims'] ?? '-'}
Base Price: ${car['basePrice'] ?? '-'}

Interior Features: $features
Exterior Options: $exterior
Safety Features: $safety
$promoSection
''';
  }

  Future<void> _generatePromo() async {
    if (_lastOrder == null) {
      return;
    }

    setState(() {
      _promoGenerating = true;
      _statusMessage = null;
    });

    final summary = _buildOrderSummary(_lastOrder!);
    final payload = {
      'model': lmStudioModel,
      'messages': [
        {
          'role': 'system',
          'content':
              'You are a helpful marketing copywriter. Write a detailed promo for the car order summary. Aim for 150-200 words.'
        },
        {
          'role': 'user',
          'content': 'Create a promo for this order:\n$summary'
        }
      ],
      'temperature': 0.7,
      'max_tokens': 400
    };

    try {
      final response = await http.post(
        Uri.parse('$lmStudioBaseUrl/v1/chat/completions'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode(payload),
      );

      if (response.statusCode >= 200 && response.statusCode < 300) {
        final data = jsonDecode(response.body) as Map<String, dynamic>;
        final choices = data['choices'] as List<dynamic>?;
        final message = choices != null && choices.isNotEmpty
            ? choices.first['message'] as Map<String, dynamic>?
            : null;
        final content = message?['content']?.toString().trim();

        setState(() {
          _promoText = (content == null || content.isEmpty)
              ? 'Promo could not be generated.'
              : content;
          _statusMessage = 'Promo generated.';
        });
      } else {
        setState(() {
          _statusMessage =
              'Promo failed (${response.statusCode}). Check LM Studio.';
        });
      }
    } catch (error) {
      setState(() {
        _statusMessage = 'Promo error: $error';
      });
    } finally {
      setState(() {
        _promoGenerating = false;
      });
    }
  }

  String _formatList(dynamic list) {
    if (list is List && list.isNotEmpty) {
      return list.join(', ');
    }
    return 'None';
  }

  Future<List<int>> _buildPdf(String content) async {
    final doc = pw.Document();
    doc.addPage(
      pw.Page(
        build: (context) => pw.Text(content),
      ),
    );
    return doc.save();
  }

  String _buildHtml(String content) {
    final escaped = const HtmlEscape().convert(content);
    return '''<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <title>Car Order Export</title>
</head>
<body>
  <pre>$escaped</pre>
</body>
</html>''';
  }

  String _buildMarkdown(String content) {
    return '## Car Order\n\n```\n$content\n```';
  }

  String _buildRtf(String content) {
    final escaped = content
        .replaceAll('\\', r'\\')
        .replaceAll('{', r'\{')
        .replaceAll('}', r'\}')
        .replaceAll('\n', r'\par ');
    return r'{\rtf1\ansi\deff0{\fonttbl{\f0 Arial;}}\f0\fs20 '
        '$escaped}';
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Car Configurator'),
      ),
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          Text(
            'Create a custom configuration and save it to H2 via the API.',
            style: Theme.of(context).textTheme.titleMedium,
          ),
          const SizedBox(height: 16),
          Form(
            key: _formKey,
            child: Column(
              children: [
                TextFormField(
                  controller: _customerController,
                  decoration: const InputDecoration(
                    labelText: 'Customer name',
                    border: OutlineInputBorder(),
                  ),
                  validator: (value) =>
                      value == null || value.trim().isEmpty
                          ? 'Enter a customer name'
                          : null,
                ),
                const SizedBox(height: 12),
                TextFormField(
                  controller: _modelController,
                  decoration: const InputDecoration(
                    labelText: 'Car model',
                    border: OutlineInputBorder(),
                  ),
                  validator: (value) =>
                      value == null || value.trim().isEmpty
                          ? 'Enter a model'
                          : null,
                ),
                const SizedBox(height: 12),
                DropdownButtonFormField<String>(
                  value: _engine,
                  decoration: const InputDecoration(
                    labelText: 'Engine',
                    border: OutlineInputBorder(),
                  ),
                  items: engineOptions
                      .map((value) =>
                          DropdownMenuItem(value: value, child: Text(value)))
                      .toList(),
                  onChanged: (value) {
                    if (value != null) {
                      setState(() => _engine = value);
                    }
                  },
                ),
                const SizedBox(height: 12),
                DropdownButtonFormField<String>(
                  value: _transmission,
                  decoration: const InputDecoration(
                    labelText: 'Transmission',
                    border: OutlineInputBorder(),
                  ),
                  items: transmissionOptions
                      .map((value) =>
                          DropdownMenuItem(value: value, child: Text(value)))
                      .toList(),
                  onChanged: (value) {
                    if (value != null) {
                      setState(() => _transmission = value);
                    }
                  },
                ),
                const SizedBox(height: 16),
                SizedBox(
                  width: double.infinity,
                  child: FilledButton.icon(
                    onPressed: _submitting ? null : _submitOrder,
                    icon: _submitting
                        ? const SizedBox(
                            width: 16,
                            height: 16,
                            child: CircularProgressIndicator(strokeWidth: 2),
                          )
                        : const Icon(Icons.save),
                    label: const Text('Save configuration'),
                  ),
                ),
              ],
            ),
          ),
          if (_statusMessage != null) ...[
            const SizedBox(height: 16),
            Text(
              _statusMessage!,
              style: TextStyle(
                color: _statusMessage!.startsWith('Error') ? Colors.red : null,
              ),
            ),
          ],
          const SizedBox(height: 24),
          if (_lastOrder != null) ...[
            Text(
              'Last order summary',
              style: Theme.of(context).textTheme.titleMedium,
            ),
            const SizedBox(height: 8),
            Container(
              padding: const EdgeInsets.all(12),
              decoration: BoxDecoration(
                color: Theme.of(context).colorScheme.surfaceContainerHighest,
                borderRadius: BorderRadius.circular(12),
              ),
              child: Text(
                _buildOrderSummary(_lastOrder!),
                style: const TextStyle(fontFamily: 'Courier'),
              ),
            ),
            const SizedBox(height: 16),
            Row(
              children: [
                FilledButton.icon(
                  onPressed: _promoGenerating ? null : _generatePromo,
                  icon: _promoGenerating
                      ? const SizedBox(
                          width: 16,
                          height: 16,
                          child: CircularProgressIndicator(strokeWidth: 2),
                        )
                      : const Icon(Icons.auto_awesome),
                  label: const Text('Generate promo'),
                ),
                const SizedBox(width: 12),
                if (_promoText != null && _promoText!.trim().isNotEmpty)
                  Expanded(
                    child: Text(
                      _promoText!,
                      maxLines: 2,
                      overflow: TextOverflow.ellipsis,
                      style: Theme.of(context).textTheme.bodySmall,
                    ),
                  ),
              ],
            ),
            const SizedBox(height: 16),
            Text(
              'Export',
              style: Theme.of(context).textTheme.titleMedium,
            ),
            const SizedBox(height: 8),
            Wrap(
              spacing: 8,
              runSpacing: 8,
              children: exportFormats
                  .map(
                    (format) => OutlinedButton(
                      onPressed: () => _export(format),
                      child: Text(format.toUpperCase()),
                    ),
                  )
                  .toList(),
            ),
          ],
        ],
      ),
    );
  }
}
