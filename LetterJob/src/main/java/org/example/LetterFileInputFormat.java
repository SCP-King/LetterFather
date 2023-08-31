package org.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;
import java.util.List;

public class LetterFileInputFormat extends FileInputFormat<Text, Text> {
    @Override
    public List<InputSplit> getSplits(JobContext job) throws IOException {
        // 返回一个自定义的InputSplit列表，这里将整个文件作为一个切片
        List<InputSplit> splits = super.getSplits(job);
        return splits;
    }

    @Override
    public RecordReader<Text, Text> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        // 返回一个自定义的RecordReader，这里将整个文件的内容作为一个Value
        LetterFileRecordReader reader = new LetterFileRecordReader();
        reader.initialize(split, context);
        return reader;
    }

    // 自定义RecordReader，将整个文件的内容作为一个Value
    public static class LetterFileRecordReader extends RecordReader<Text, Text> {
        private Path file;
        private Configuration conf;
        private boolean processed = false;
        private Text key;
        private Text value;

        @Override
        public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
            file = ((FileSplit) split).getPath();
            conf = context.getConfiguration();
        }

        @Override
        public boolean nextKeyValue() throws IOException, InterruptedException {
            if (!processed) {
                byte[] contents = new byte[(int) file.getFileSystem(conf).getFileStatus(file).getLen()];
                file.getFileSystem(conf).open(file).readFully(0, contents);
                String contentString = new String(contents);
                key = new Text(file.getName());
                value = new Text(contentString);
                processed = true;
                return true;
            }
            return false;
        }

        @Override
        public Text getCurrentKey() throws IOException, InterruptedException {
            return key;
        }

        @Override
        public Text getCurrentValue() throws IOException, InterruptedException {
            return value;
        }

        @Override
        public float getProgress() throws IOException, InterruptedException {
            return processed ? 1.0f : 0.0f;
        }

        @Override
        public void close() throws IOException {
            //不进行操作
        }
    }
}