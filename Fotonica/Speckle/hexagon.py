import numpy as np

def hexagono(ld):
    raiz3 = np.sqrt(3)
    altura = int(raiz3*ld/2)
    xSz = 2*altura
    ySz = 2*ld

    lente = np.zeros((xSz, ySz))

    lente[:, ld/2:3*ld/2] = 1

    for x in range(0, ld/2):
        rX = x
        yTeo1 = (-raiz3) * rX + raiz3*25
        yTeo2 = (raiz3) * rX

        for y in range(0, altura):
            rY = altura - y

            # Arriba a la derecha
            if rY < yTeo1:
                lente[y][x+(3*ld/2)] = 1
            # Abajo a la derecha
            if rY > yTeo2:
                lente[altura+y][x+(3*ld/2)] = 1
            # Arriba a la Izquierda
            if rY < yTeo2:
                lente[y][x] = 1
            # Abajo a la izquierda
            if rY > yTeo1:
                lente[altura+y][x] = 1

    return lente
