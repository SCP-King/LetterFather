package org.example;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class LetterReducer extends Reducer<Text,Text, NullWritable,Text> {
    private Text outV=new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, NullWritable, Text>.Context context) throws IOException, InterruptedException {
        StringBuilder s=new StringBuilder();
        for(Text i:values){
            s.append(i.toString()+"\t");
        }
        outV.set(s.toString());
        context.write(NullWritable.get(),outV);
    }
}
