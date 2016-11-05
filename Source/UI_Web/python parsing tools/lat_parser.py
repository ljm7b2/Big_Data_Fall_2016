pts = open("randomLatLong.txt", "r")

out = open("lat_data.txt", "w")

for line in pts:
    lat, long = line.split(",")

    print("new google.maps.LatLng(" + lat + ", " + long.strip("\n") + "),", file=out)


pts.close()
out.close()
