import os.path
import re
import difflib
path = 'C:/Users/kmg/Desktop/'
from NumberToKorean import digit2txt

with open(path + 'test_result.txt', 'w'):
    print("write")


# 일치 않는 문자열 텍스트 파일로 생성
with open(path + 'transcript.v.1.2.txt', 'r', encoding='UTF8') as f:
    lines = f.readlines()
    with open(path + 'result.txt', 'w'):
        with open(path + 'test.txt', 'w'):
            for line in lines:
                str = line.split('|')

                with open(path + 'test.txt', 'a') as i:
                    i.write(str[1] + "\n")

                with open(path + 'result.txt', 'a') as r:
                    r.write(str[0] + "|" + str[1] + "|" + str[2] + "\n")


# 숫자가 포함된 텍스트 파일로 생성
with open(path + 'result.txt', 'r') as t:
    reObj = re.compile('\d+')
    lines = t.readlines()
    for line in lines:
        str = line.split("|")
        matchObj = reObj.search(str[1])
        if matchObj:
            with open(path + 'number.txt', 'a') as nu:
                nu.write(str[2])


def convertToKorean(matchObj):
    return digit2txt(matchObj.group())

# 숫자를 한글로 변환
with open(path + 'test.txt', 'r') as t:
    reObj = re.compile('\d+')
    lines = t.readlines()
    for line in lines:
        matchObj = reObj.search(line)
        if matchObj:
            with open(path + 'test_result.txt', 'a') as tr:
                tr.write(re.sub(pattern=r'\d+', repl=convertToKorean, string=line))



