"""
    Programa para la obtencion de la constante de Madelung para una esctructura
    2-D de NaCl mediante el procedimiento de Evjen. se realiza el calculo
    iterativo hasta que la contribucion de la zona sea menor que <0.001

    (x, y, z) => (k, j, i) siendo z la profundidad, y la altura y x la anchura
"""

import numpy as np

def crearRed(zona):
    length = zona+1
    red = np.zeros((1, length, length))

    for i in range(1):
        for j in range(length):
            for k in range(length):
                xyz = (i+j+k)/zona

                if xyz != 0:
                    red[i][j][k] += 1/(2.0**xyz)
    return red

def calcMadelung(red):
    madelung = 0

    for j in range(len(red[0])):
        for k in range(1, len(red[0][0])):
            i = 0
            if i == len(red):
                pij2 = i*i+j*j+k*k
                xyz = i+j+k

                if xyz % 2 == 0:
                    madelung += red[i][j][k]/np.sqrt(pij2)
                else:
                    madelung -= red[i][j][k]/np.sqrt(pij2)
            elif (j == len(red[0])-1 or k == len(red[0][0])-1):
                pij2 = i*i+j*j+k*k
                xyz = i+j+k

                if xyz % 2 == 0:
                    madelung -= red[i][j][k]/np.sqrt(pij2)
                else:
                    madelung += red[i][j][k]/np.sqrt(pij2)
    return 4*madelung

madelung = 0
zonas = [1,2,3]

for zona in zonas:
    if zona > 1:
        red = 1- red
        madelung += calcMadelung(red)

    red = crearRed(zona)
    madelung += calcMadelung(red)
    print madelung
