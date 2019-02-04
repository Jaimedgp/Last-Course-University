import numpy as np
import scipy.integrate as integ
import matplotlib.pyplot as plt

class CalcCosmologica():
    """                                                                     """
    """   Calculadora cosmologica escrita para la asignatura de Astrofisica """
    """   del ultimo curso del grado en fisica.                             """
    """                                                                     """

    global a0
    global z
    global E

    def __init__(self, radOmega=0, mateOmega=0.24, lmbdOmega=0.76, hubble0=71.0):
        c = 2.998*(10**8) # set speed of ligth
        self.rdOmg = radOmega
        self.mtrOmg = mateOmega
        self.lmbdOmg = lmbdOmega
        self.kOmg = 1- (radOmega + mateOmega + lmbdOmega)

        self.hbbl0 = float(hubble0)

        self.E = lambda z: np.sqrt(self.lmbdOmg + self.kOmg*(1+z)**2 +
                            self.mtrOmg*(1+z)**3 + self.rdOmg*(1+z)**4)
        self.a = lambda z: 1/(1+z)

    def time(self, z=[float(i) for i in range(10)]):
        t = [0 for i in range(len(z))]
        for i in range(len(z)):
            a, err =  integ.quad(lambda x: 1/((1+x)*self.E(x)), 0, z[i])
            t[i] = a/self.hbbl0
        return t

