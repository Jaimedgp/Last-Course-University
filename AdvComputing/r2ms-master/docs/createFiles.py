for i in range(10):
    with open('inputs/input_'+str(i)+'.txt', 'w') as fw:
        fw.write("latticeLength"+"\t"+"5"+"\n")
        fw.write("temperature"+"\t"+str(0+(i*100))+"\t"+"K"+"\n")
        fw.write("therm"+"\t"+"25000"+"\n")
        fw.write("H"+"\t"+"0.01"+"\t"+"A/m"+"\n")
        fw.write("skip"+"\t"+"1000"+"\n")
        fw.write("mcs"+"\t"+"100000"+"\n")
        fw.write("J"+"\t"+"2.0 2.243052E-22 1.121526E-22"+"\t"+"J"+"\n")
