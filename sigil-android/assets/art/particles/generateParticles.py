import math

def expandTemplate(name, num):
    tn = name + ".template"
    fn = name + ".p"
    t = open(tn).read()
    data = ""
    for i in range(num):
        npe = t
        angle = 360.0 / num * i
        npe = npe.replace("$NAME", "%d" % (i))
        npe = npe.replace("$X0", "0")
        npe = npe.replace("$Y0", "0")
        npe = npe.replace("$A0", "%0.4f" % (angle))
        npe = npe.replace("$X1", "%0.4f" % (math.cos(math.radians(angle)) * 100))
        npe = npe.replace("$Y1", "%0.4f" % (math.sin(math.radians(angle)) * 100))
        npe = npe.replace("$A1", "%0.4f" % (angle - 180))
        #npe = npe.replace("$IMAGE", "particle.png")
        npe = npe.replace("$IMAGE", "firelarge1.png")

        data += npe + "\n\n"

    f = open(fn, 'w')
    f.write(data.strip())
    f.close()

expandTemplate("summon", 32)
expandTemplate("banish", 32)
