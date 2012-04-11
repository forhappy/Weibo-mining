#!/usr/bin/python
# -*- coding: gbk -*-
import os
import sys

#userdict_handler = open("userdict.txt", "w")

filenames=os.listdir(os.getcwd()+"/userdict")
for filename in filenames:
    print filename
    file_handler = open(filename, "r")
    lines = file_handler.readlines()
    for line in lines:
        coding_converted = unicode(line, "gbk")
        pinyin_trimmed = coding_converted[:(coding_converted.find(" ") + 1)]
        userdict = pinyin_trimmed.replace(" ", "@@")
#        userdict_handler.write(userdict)
        print userdict
    
    file_handler.close()

#userdict_handler.close()
