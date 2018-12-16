from Espejo import EspejoResonador as Espejo

import matplotlib.pyplot as plt
import numpy as np

class Speckle:

    def __init__(self, smllSz, telescopeSize, bigSize):

        self.bgSz = bigSize
        self.tlscpSz = telescopeSize

        self.ySz = 2*smllSz
        self.xSz = 2*int(np.sqrt(3)*smllSz/2)

        self.rate = (self.tlscpSz/self.ySz)+4
        self.pupils = [None, None]

    def pupilMesh(self, m, random):

        tlsSz = self.tlscpSz + 3*self.ySz

        numH = (tlsSz-self.ySz) / (3*self.ySz/2)

        Pupil = Espejo(self.xSz, self.ySz, self.ySz/2)
        Pupil.lenteHexagonal()

        pupilMesh = np.zeros((self.rate*self.xSz, tlsSz), dtype=np.complex_)

        pupils = []

        xStart = -m*self.xSz/2
        yInterval = 3*self.ySz/2
        yStart = -m*yInterval/2

        length =  ((numH+1)*yInterval + yStart) + self.ySz
        if length <= tlsSz:
            numH += 1

        for i in range(m, self.rate):
            xPix = i*self.xSz + xStart
            for k in range(m, numH+1):
                yPix = k*yInterval + yStart

                if random:
                    Pupil.moverEspejoAleatorio(90)

                smllPupil = Pupil.lente

                pupil = np.zeros((self.rate*self.xSz, tlsSz), dtype=np.complex_)
                pupil[xPix:xPix+self.xSz, yPix:yPix+self.ySz] = smllPupil[:]
                
                pupils.append(pupil)
                pupilMesh += pupil

        self.pupils[m] = pupils

        return pupilMesh

    def xtndPupilMesh(self, pupilMesh):


        extndMrr = np.zeros((self.bgSz, self.bgSz), dtype=np.complex_)

        xInit = (self.bgSz-len(pupilMesh))/2
        yInit = (self.bgSz-len(pupilMesh[0]))/2

        # Extend mesh for a squared mesh with 0s
        extndMrr[0:xInit, 0:yInit] = 0
        extndMrr[-xInit:, -yInit:] = 0
        extndMrr[xInit:-xInit, yInit:-yInit] = pupilMesh[:, :]

        return extndMrr

    def getSpeckle(self, curv, rand):

        pupilMesh = self.pupilMesh(0, rand) + self.pupilMesh(1, rand)

        if self.rate % 2 == 0:
            pupilMesh[self.xSz/2:, :] = pupilMesh[: -self.xSz/2, :]
        elif self.rate % 2 == 0:
            pupilMesh[:, :-self.ySz/4] = pupilMesh[:, self.ySz/4:]

        pupilMesh = self.xtndPupilMesh(pupilMesh)

        bgPupil = Espejo(self.bgSz, self.bgSz, self.tlscpSz, curvatura=curv)
        bgPupil.lenteCircular()
        optic = pupilMesh*bgPupil.lente
        
        return optic
