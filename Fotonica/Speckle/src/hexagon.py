import numpy as np

def hexagono(ld):
    raiz3 = np.sqrt(3)
    altura = int(raiz3*ld/2)
    xSz = 2*altura
    ySz = 2*ld

    lente = np.zeros((xSz, ySz))

    lente[1:-1, ld/2:3*ld/2] = 1
    lente[1, ld/2:3*ld/2] = 0.5
    lente[-1, ld/2:3*ld/2] = 0.5

    for x in range(0, ld/2):
        rX = x
        yTeo1 = int(-raiz3 * rX + altura)
        yTeo2 = int(raiz3 * rX)

        for y in range(0, altura):
            rY = altura - y

            # Arriba a la derecha
            if rY < yTeo1:
                lente[y][x+(3*ld/2)] = 1
            elif rY == yTeo1:
                lente[y][x+(3*ld/2)] = 0.5

            # Abajo a la derecha
            if rY > yTeo2:
                lente[altura+y][x+(3*ld/2)] = 1
            elif rY == yTeo2:
                lente[altura+y][x+(3*ld/2)] = 0.5

            # Arriba a la Izquierda
            if rY < yTeo2:
                lente[y][x] = 1
            elif rY == yTeo2:
                lente[y][x] = 0.5

            # Abajo a la izquierda
            if rY > yTeo1:
                lente[altura+y][x] = 1
            elif rY == yTeo1:
                lente[altura+y][x] = 0.5
    return lente
