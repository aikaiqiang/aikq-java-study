package com.space.aikq.stream;

import com.google.common.collect.Lists;

import java.util.List;

/**
 *
 */
public class StreamTest {


    public static void main(String[] args) {

        List list = Lists.newArrayList();

        list.parallelStream();

        list.stream().parallel();



    }
}
