"""
    Programa para la obtencion de la constante de Madelung para una esctructura
    2-D de NaCl mediante el procedimiento de Evjen. se realiza el calculo
    iterativo hasta que la contribucion de la zona sea menor que <0.001
"""

import numpy as np

def crearRed(zona):
    length = zona+1
    red = np.zeros((length, length, 1))

    for i in range(length):
        for j in range(length):
            for k in range(1):
                xyz = i+j+k

                if xyz != 0:
                    red[i][j][k] += 1/(2.0**xyz)
    return red

def calcMadelung(red):
    madelung = 0

    for i in range(len(red)):
        for j in range(len(red)):
            k = 0
            if i == len(red):
                pij2 = i*i+j*j+k*k

                if pij2 % 2 == 0:
                    madelung += red[i][j][k]/np.sqrt(pij2)
                else:
                    madelung -= red[i][j][k]/np.sqrt(pij2)
            elif (j == len(red)-1 or k == len(red)-1):
                pij2 = i*i+j*j+k*k

                if pij2 % 2 == 0:
                    madelung -= red[i][j][k]/np.sqrt(pij2)
                else:
                    madelung += red[i][j][k]/np.sqrt(pij2)
    return 4*madelung

red = crearRed(1)
print calcMadelung(red)
