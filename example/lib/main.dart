import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:my_video_compress/my_video_compress.dart';
import 'package:image_picker/image_picker.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _fileInputPath = "";
  String _fileOutputPath = "";
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
          appBar: AppBar(
            title: const Text('Plugin example app'),
          ),
          body: Column(
            children: <Widget>[
              Row(
                children: <Widget>[
                  RaisedButton(
                    child: Text("Select File"),
                    onPressed: () async {
                      var image = await ImagePicker.pickVideo(
                          source: ImageSource.gallery);
                      setState(() {
                        _fileInputPath = image.path;
                      });
                    },
                  ),
                  Container(
                    width: 20,
                  ),
                  RaisedButton(
                    child: Text("Start Compress"),
                    onPressed: () async{
                      String destPath = await MyVideoCompress.videoCompress(_fileInputPath);
                      setState(() {
                        _fileOutputPath = destPath;
                      });
                    },
                  )
                ],
              ),
              Row(
                children: <Widget>[
                  Text(
                    "Input: ",
                    style: TextStyle(fontSize: 20),
                  ),
                  Expanded(
                    child: Text(_fileInputPath),
                  )
                ],
              ),
              Row(
                children: <Widget>[
                  Text(
                    "Output: ",
                    style: TextStyle(fontSize: 20),
                  ),
                  Expanded(
                    child: Text(_fileOutputPath),
                  )
                ],
              )
            ],
          )),
    );
  }
}
