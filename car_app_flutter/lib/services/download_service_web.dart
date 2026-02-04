// ignore: avoid_web_libraries_in_flutter
import 'dart:html' as html;
import 'dart:convert';

Future<void> downloadTextImpl(String filename, String content) async {
  final bytes = utf8.encode(content);
  await downloadBytesImpl(filename, bytes);
}

Future<void> downloadBytesImpl(String filename, List<int> bytes) async {
  final blob = html.Blob([bytes], 'application/octet-stream');
  final url = html.Url.createObjectUrlFromBlob(blob);
  final anchor = html.AnchorElement(href: url)
    ..download = filename
    ..style.display = 'none';
  html.document.body?.append(anchor);
  anchor.click();
  anchor.remove();
  html.Url.revokeObjectUrl(url);
}
