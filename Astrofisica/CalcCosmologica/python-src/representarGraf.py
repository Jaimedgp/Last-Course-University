import numpy as np
import matplotlib.pyplot as plt

class Representar():
    def __init__(self, universos, z, t):
        self.universos = universos
        self.t = t
        self.z = z

    def distanciaDimAngular(self):
        fig  = plt.figure()

        for name, univ in self.universos.items():
            if name != "$\Lambda$":
                disAng = univ.distanciaDimAng(self.z)
                plt.plot(self.z, disAng, label=name)
        plt.legend()
        plt.xlabel("z", fontsize=15)
        plt.ylabel("$D_A$ [Mpc]", fontsize=15)
        fig.savefig("./Graficas/DistanDiamAngular.png")

        fig  = plt.figure()
        disAng = self.universos["$\Lambda$"].distanciaDimAng(self.z)
        plt.plot(self.z, disAng, label="$\Lambda$")
        plt.legend()
        plt.xlabel("z", fontsize=15)
        plt.ylabel("$D_A$ [Mpc]", fontsize=15)
        fig.savefig("./Graficas/DistanDiamAngular(EnrgOsc).png")

    def distanciaDimLuminosidad(self):
        fig  = plt.figure(figsize=(8,6))

        for name, univ in self.universos.items():
            if name != "$\Lambda$":
                disLum = univ.distanciaDimLum(self.z)
                plt.plot(self.z, disLum, label=name)
        plt.ticklabel_format(style='sci', axis='x')
        plt.legend()
        plt.xlabel("z", fontsize=15)
        plt.ylabel("$D_L$ [Mpc]", fontsize=15)
        fig.savefig("./Graficas/DistanDiamLumino.png")

        fig  = plt.figure(figsize=(8,6))
        disLum = self.universos["$\Lambda$"].distanciaDimLum(self.z)
        plt.plot(self.z, disLum, label="$\Lambda$")
        plt.legend()
        plt.xlabel("z", fontsize=15)
        plt.ylabel("$D_L$ [Mpc]", fontsize=15)
        fig.savefig("./Graficas/DistanDiamLumino(EnrgOsc).png")

    def factorEscala(self):
        fig  = plt.figure()

        for name, univ in self.universos.items():
            a = univ.a(t=self.t)
            plt.plot(self.t, a, label=name)
        plt.legend()
        plt.xlabel("t [Gyr]", fontsize=15)
        plt.ylabel("a(t)", fontsize=15)
        fig.savefig("./Graficas/factorEscala.png")

    def hubble(self):
        fig  = plt.figure()

        for name, univ in self.universos.items():
            h = univ.hubble(self.t)
            plt.plot(self.t, h, label=name)
        plt.legend()
        plt.xlabel("t [Gyr]", fontsize=15)
        plt.ylabel("H(t) [Gyr$^{-1}$]", fontsize=15)
        fig.savefig("./Graficas/H(t).png")

    def radioHubble(self):
        fig  = plt.figure()

        for name, univ in self.universos.items():
            rdHbbl = univ.radioHubble(self.t)
            plt.plot(self.t, rdHbbl, label=name)
        plt.legend()
        plt.xlabel("t [Gyr]", fontsize=15)
        plt.ylabel("Radio de Hubble [Mpc]", fontsize=15)
        fig.savefig("./Graficas/radio_Hubble.png")

    def horizntParticulas(self):
        fig  = plt.figure()

        for name, univ in self.universos.items():
            t, horzPrt = univ.horrizntPart(self.t)
            plt.plot(t, horzPrt, label=name)
        plt.legend()
        plt.xlabel("t [Gyr]", fontsize=15)
        plt.ylabel("Horizonte de Particulas [Mpc]", fontsize=15)
        fig.savefig("./Graficas/horizonte_Particulas.png")

    def edadUniverso(self):
        print self.universos["Actual"].tiempo([np.inf])

        fig  = plt.figure()

        for name, univ in self.universos.items():
            eddUnvrs = univ.tiempo(self.z)
            plt.plot(self.z, eddUnvrs, label=name)
        plt.legend()
        plt.xlabel("z", fontsize=15)
        plt.ylabel("Edad del Universo [Gyr]", fontsize=15)
        fig.savefig("./Graficas/edad_Universo.png")
