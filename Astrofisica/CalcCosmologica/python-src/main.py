from representarGraf import Representar
from CalcCosmlgc import CalcCosmologica
import numpy as np

universos = {"Actual": CalcCosmologica(radOmega = 0.0,
                                       mateOmega = 0.315,
                                       lmbdOmega = 0.685,
                                       hubble0 = 0.06888),
             "Radiacion": CalcCosmologica(radOmega = 1.0,
                                         mateOmega = 0.0,
                                         lmbdOmega = 0.0,
                                         hubble0 = 0.07),
             "Materia": CalcCosmologica(radOmega = 0.0,
                                         mateOmega = 1.0,
                                         lmbdOmega = 0.0,
                                         hubble0 = 0.07),
             "$\Lambda$": CalcCosmologica(radOmega = 0.0,
                                         mateOmega = 0.0,
                                         lmbdOmega = 1.0,
                                         hubble0 = 0.07)}

z = np.linspace(0, 15, 1000)
t = np.linspace(0, 50, 1000)

graficas = Representar(universos, z, t)

#graficas.distanciaDimAngular()
#graficas.distanciaDimLuminosidad()
#graficas.hubble()
#graficas.factorEscala()
#graficas.hubble()
#graficas.radioHubble()
#graficas.horizntParticulas()
graficas.edadUniverso()
