"""
Webarchive to HTML
"""

import os

def fileReader(directory : str) -> list:
    pathList = []
    for mainDirc, subDirc, fileNameList in os.walk(directory):
        for fileName in fileNameList:
            filePath = os.path.join(mainDirc, fileName)
            pathList.append(filePath)
    return pathList

def webArchiveToHmtl(directory : str) -> None:
    filePathList = fileReader(directory)
    commandList = []
    for filePath in filePathList:
        noSpacePath = filePath.replace(' ', '-')
        cmpltCommand = 'textutil -convert html ' + noSpacePath
        commandList.append(cmpltCommand)
    for cmd in commandList:
        os.system(cmd)

webArchiveToHmtl(fileReader("/Users/ergou/Desktop/SGFC/DCS/SCMP"))
