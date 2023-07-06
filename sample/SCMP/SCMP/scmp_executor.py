"""
Parse through SCMP webarchive/html files to count occurences of key phrases and export as excel file

Author: JTYH
"""

import os
from bs4 import BeautifulSoup
import pandas as pd
import numpy as np

"""
3. Main Processing Operations

Note that this is only valid for the articles retrieved from Factiva database
"""
def htmlParser(fileList : list):
    
    for filePath in fileList:
        # setup global varibles
        soup = BeautifulSoup(open(filePath), 'html.parser')
        dataFrame = []
        articleVector = []
        articleCounter = 1
        tempp7 = None

        for element in soup.find_all():
            # class <p6> is the article title, which is also the start of an article
            if element.name == "p" and "class" in element.attrs and "p6" in element["class"]:
                # if it's not the first article, process the previous completed article vector and append it to the data frame list
                if articleCounter != 1:
                    # slice out the text body and make it into one element in the article vector 
                    textBody = articleVector[5:]
                    fullText = ''.join(textBody)
                    articleVector = articleVector[:5]
                    articleVector.append(fullText)
                    dataFrame.append(articleVector)
                    articleVector = []
                else:
                    articleVector.append(articleCounter) # 1st item in articleVector is article number
                    articleTitle = element.text.strip()
                    articleVector.append(articleTitle) # 2nd item in articleVector is title
                    articleCounter += 1

            # class <p7> contains information including (sequentially) word count, publish date and publisher
            if element.name == "p" and "class" in element.attrs and "p7" in element["class"]:
                tempp7 = element.text.strip()
                tempp7List = tempp7.split(" ")
                if tempp7List[-1] == 'words':
                    articleVector.append(int(tempp7List[0])) # 3rd item in articleVector is word count in Int
                elif tempp7List[-1].isnumeric():
                    articleVector.append(tempp7) # 4th item in articleVector is publish date
                elif tempp7 == "scmp.com":
                    articleVector.append('SCMP') # 5th item in articleVector is publisher (SCMP)
                elif tempp7 == "The Standard":
                    articleVector.append('The Standard') # 5th item in articleVector is publisher (The Standard)
                else: next

            # class <p8> contains text body (in different paragraphs for seperated classes "p8"), and they are all in >=6 items
            elif element.name == "p" and "class" in element.attrs and "p8" in element["class"]:
                articleVector.append(element.text.strip())
        
        # adding the column names and transform the 2D list into a data frame
        transformDataFrame = pd.DataFrame(np.array(dataFrame),
                                        columns=['Number', 'Headline', 'Word Count', 'Date', 'Publisher', 'Text'])
        
        return transformDataFrame
        

"""
test for function 3
"""
fileAddr = '/Users/ergou/Desktop/SGFC/DCS/Articles/dual-class-1.html'
print(htmlParser([fileAddr]))

"""
4. DataFrame to Excel
"""
def DFToExcel(df, keyPhrases : str, excelFile : str):

    writer = pd.ExcelWriter(excelFile, engine = 'xlsxwriter')

    # counting occurences of each of the phrase in the text bodies
    for phrase in keyPhrases:
        df[phrase] = df['Text'].str.count(phrase)
    sheetName = filePath.split("/")[-1].split(".")[-2]
    df.to_excel(writer, sheet_name = sheetName)
    
    writer.close()




if __name__ == "__main__":
    htmlDirectory = ""
    targetPhrases = ["dual", "class", "share", "shares", "weighted", "voting", "right", "rights", "structure", "capital", "inflow", "outflow"]
    htmlCounterToExcel(htmlDirectory, targetPhrases)
    




