with open("ex.csv") as infile, open("outfile.csv", "w") as outfile:
    for line in infile:
        outfile.write(line.replace(",", ""))
