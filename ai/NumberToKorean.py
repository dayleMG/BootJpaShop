#-*- coding:utf-8 -*-

# 만 단위 자릿수
tenThousandPos = 4
# 억 단위 자릿수
hundredMillionPos = 9
txtDigit = ['', '십', '백', '천', '만', '억']
txtNumber = ['', '일', '이', '삼', '사', '오', '육', '칠', '팔', '구']

def digit2txt(strNum):
    resultStr = ''
    digitCount = 0
    print(strNum)

    #자릿수 카운트
    for ch in strNum:
        digitCount = digitCount + 1

    digitCount = digitCount-1
    index = 0

    while True:
        notShowDigit = False
        ch = strNum[index]
        #자릿수가 2자리이고 1이면 '일'은 표시 안함.
        # 단 '만' '억'에서는 표시 함
        if(digitCount > 1) and (digitCount != tenThousandPos) and  (digitCount != hundredMillionPos) and int(ch) == 1:
            resultStr = resultStr + ''
        elif int(ch) == 0:
            resultStr = resultStr + ''
            # 단 '만' '억'에서는 표시 함
            if (digitCount != tenThousandPos) and  (digitCount != hundredMillionPos):
                notShowDigit = True
        else:
            resultStr = resultStr + txtNumber[int(ch)]


        # 1억 이상
        if digitCount > hundredMillionPos:
            if not notShowDigit:
                resultStr = resultStr + txtDigit[digitCount-hundredMillionPos]
        # 1만 이상
        elif digitCount > tenThousandPos:
            if not notShowDigit:
                resultStr = resultStr + txtDigit[digitCount-tenThousandPos]
        else:
            if not notShowDigit:
                resultStr = resultStr + txtDigit[digitCount]

        if digitCount <= 0:
            digitCount = 0
        else:
            digitCount = digitCount - 1
        index = index + 1
        if index >= len(strNum):
            break;
    return resultStr + " "
