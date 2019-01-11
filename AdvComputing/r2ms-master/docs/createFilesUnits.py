for i in range(10):
    with open('inputsUnits/input_'+str(i)+'.txt', 'w') as fw:
        fw.write("latticeLength"+"\t"+"10"+"\n")
        fw.write("temperature"+"\t"+str(0+(i*100))+"\t"+"K"+"\n")
        fw.write("therm"+"\t"+"25000"+"\n")
        fw.write("H"+"\t"+"0.01"+"\t"+"A/m"+"\n")
        fw.write("skip"+"\t"+"1000"+"\n")
        fw.write("mcs"+"\t"+"100000"+"\n")
        fw.write("J"+"\t"+"2.0 0.0014 0.0007"+"\t"+"eV"+"\n")
