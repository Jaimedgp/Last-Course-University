import matplotlib.pyplot as plt
import numpy as np

class Population():
    def __init__(self, A, x0, i):
        self.A = A
        self.x0 = x0
        self.i = i

        self.simulation()

    def simulation(self):

        self.x = np.zeros(len(self.i))
        self.xp1 = np.zeros(len(self.i)-1)
        self.x[0] = self.x0

        for j in range(1, len(i)):
            self.x[j] = 4*self.A*(self.x[j-1] - self.x[j-1]**2)
            self.xp1[j-1] = self.x[j]

    def stepFunction(self):
        x = self.x
        y = self.xp1

        xs = np.zeros(2*len(y))
        xs[0] = x[0]
        ys = np.zeros(2*len(y))
        ys[0] = y[0]

        for j in range(len(y)):
            index = 2*j
            xs[index:index+2] = [x[j], x[j+1]]
            ys[index:index+2] = [y[j], y[j]]

        return [xs, ys]

    def curve_fit1(self):
        x = self.x[:-1]
        y = self.xp1

        grade = len(y)-1

        xTh = np.linspace(0, 1, 10*grade)
        yTh = np.linspace(0, 1, 10*grade)
        grade = 2
        parameters = np.polyfit(x, y, grade)

        for j in range(len(xTh)):
            yTh[j] = 0
            for i in range(grade):
                yTh[j] += parameters[i]*xTh[j]**(grade-i)

        return xTh, yTh

    def curve_fit2(self):
        x = self.x[1:]
        y = self.xp1

        grade = len(y)-1

        xTh = np.linspace(0, 1, 10*grade)
        yTh = np.linspace(0, 1, 10*grade)
        grade = 2
        parameters = np.polyfit(x, y, grade)

        for j in range(len(xTh)):
            yTh[j] = 0
            for i in range(grade):
                yTh[j] += parameters[i]*xTh[j]**(grade-i)

        return xTh, yTh


if __name__ == '__main__':

    fig, axs = plt.subplots(1, 2)

    #---------------------------------------------
    #       POPULATION 1
    #---------------------------------------------

    i = np.asarray([ i for i in range(0, 80)])

    Population1 = Population(1.00, 0.01, i)

    xTh, yTh = Population1.curve_fit1()
    axs[0].plot(xTh, yTh, 'gray')

    xTh, yTh = Population1.curve_fit2()
    axs[0].plot(xTh, yTh, 'gray')

    xs, ys = Population1.stepFunction()
    axs[0].plot(xs, ys, 'r')

    axs[1].plot(Population1.i, Population1.x, 'r')

    #---------------------------------------------
    #       POPULATION 2
    #---------------------------------------------

    i = np.asarray([ i for i in range(0, 80)])

    Population2 = Population(0.5, 0.01, i)

    xTh, yTh = Population2.curve_fit1()
    axs[0].plot(xTh, yTh, 'gray')

    xTh, yTh = Population2.curve_fit2()
    axs[0].plot(xTh, yTh, 'gray')

    xs, ys = Population2.stepFunction()
    axs[0].plot(xs, ys, 'b')

    axs[1].plot(Population2.i, Population2.x, 'b')

    #---------------------------------------------
    #       POPULATION 3
    #---------------------------------------------

    i = np.asarray([ i for i in range(0, 80)])

    Population3 = Population(0.8, 0.01, i)

    xTh, yTh = Population3.curve_fit1()
    axs[0].plot(xTh, yTh, 'gray')

    xTh, yTh = Population3.curve_fit2()
    axs[0].plot(xTh, yTh, 'gray')

    xs, ys = Population3.stepFunction()
    axs[0].plot(xs, ys, 'g')

    axs[1].plot(Population3.i, Population3.x, 'g')

    #---------------------------------------------
    #       POPULATION 4
    #---------------------------------------------

    i = np.asarray([ i for i in range(0, 80)])

    Population4 = Population(0.24, 0.5, i)

    xTh, yTh = Population4.curve_fit1()
    axs[0].plot(xTh, yTh, 'gray')

    xTh, yTh = Population4.curve_fit2()
    axs[0].plot(xTh, yTh, 'gray')

    xs, ys = Population4.stepFunction()
    axs[0].plot(xs, ys, 'k')

    axs[1].plot(Population4.i, Population4.x, 'k')

    axs[0].set_ylim(0, 1)
    axs[0].set_xlim(0, 1)
    plt.show()
