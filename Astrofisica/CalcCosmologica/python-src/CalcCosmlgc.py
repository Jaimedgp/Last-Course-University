import numpy as np
import scipy.integrate as integ
import matplotlib.pyplot as plt

a0 = 1
c = 306.3595255 #velocidad de la luz en Mpc/Gyr

class CalcCosmologica(object):
    """                                                                     """
    """   Calculadora cosmologica escrita para la asignatura de Astrofisica """
    """   del ultimo curso del grado en fisica.                             """
    """                                                                     """

    global a0, c

    def __init__(self, radOmega=0.0, mateOmega=0.315, lmbdOmega=0.685,
                                                                hubble0=0.06888):
        self.rdOmg = radOmega
        self.mtrOmg = mateOmega
        self.lmbdOmg = lmbdOmega
        self.kOmg = 1-(radOmega + mateOmega + lmbdOmega)

        self.hbbl0 = hubble0

        self.k = - self.kOmg*self.hbbl0**2/c**2

        self.E = lambda z: np.sqrt(self.lmbdOmg + self.kOmg*(1+z)**2 +
                            self.mtrOmg*(1+z)**3 + self.rdOmg*(1+z)**4)

    def tiempo(self, z):
        t = np.zeros(len(z))
        for i in range(len(z)):
            a, err =  integ.quad(lambda x: 1/((1+x)*self.E(x)), 0, z[i])
            t[i] = a/self.hbbl0
        return t

    def a(self, z=None, t=None):
        isZ = (z is not None)
        isT = (t is not None)

        if isZ and isT:
            print ("Eeheheheheheh tio aclarate, o una cosa o la otra pero en"+
                                     +" funcion de z y de t a la vez no!!!!")
            return -1
        elif isZ and not isT:
            return a0/(1+z)
        elif not isZ and isT:
            isFloat = (type(t) == float)
            if isFloat:
                t = [0,t]
            aPunto = lambda t, a:a*self.hbbl0*np.sqrt(self.lmbdOmg +
                                                    self.kOmg/a**2 +
                                                    self.mtrOmg/a**3 +
                                                   self.rdOmg/a**4)
            a = np.zeros((len(t), 1))
            a[0, :] = 1
            r = integ.ode(aPunto).set_integrator("dopri5")
            r.set_initial_value(1, 0)
            for i in range(1, len(t)):
                a[i, :] = r.integrate(t[i])

            if isFloat:
                return a[-1]
            else:
                return a

    def coordndRadl(self, z):
        integral = np.zeros(len(z))
        for i in range(len(z)):
            integral[i], err = integ.quad(lambda x:1/self.E(x), 0, z[i])

        if self.k == 0:
            coordndRadl = (c/(a0*self.hbbl0))*(integral)
        else:
            coordndRadl = ((c/(a0*self.hbbl0*np.sqrt(self.kOmg)))*
                                          np.sinh(np.sqrt(self.kOmg)*integral))

        return coordndRadl

    def distanciaDimLum(self, z):
        sig = self.coordndRadl(z)
        return a0*(1+z)*sig

    def distanciaDimAng(self, z):
        return self.distanciaDimLum(z)/((1+z)**2)

    def radioHubble(self, t):
        return c/self.hubble(t)

    def horrizntPart(self, t):
        intInvAt = np.zeros(len(t))
        a = self.a(t=t)

        for i in range(len(t)):
            intInvAt[i], err = integ.quad(lambda x:c/self.a(t=float(x)), 0, t[i])

        return t, intInvAt

    def hubble(self, t):
        a = self.a(t=t)
        H = self.hbbl0*np.sqrt(self.lmbdOmg + self.kOmg/a**2 +
                                              self.mtrOmg/a**3 +
                                              self.rdOmg/a**4)
        return H
