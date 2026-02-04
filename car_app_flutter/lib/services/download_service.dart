import 'download_service_stub.dart'
    if (dart.library.html) 'download_service_web.dart';

Future<void> downloadText(String filename, String content) {
  return downloadTextImpl(filename, content);
}

Future<void> downloadBytes(String filename, List<int> bytes) {
  return downloadBytesImpl(filename, bytes);
}
