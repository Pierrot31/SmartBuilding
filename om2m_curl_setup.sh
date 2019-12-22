#!/bin/bash

# Creation of the rooms :
for room in ROOM{01,02} ; do
	curl -X POST 'http://127.0.0.1:8080/~/in-cse' -H 'cache-control: no-cache' -H 'content-type: application/xml;ty=2' -H 'x-m2m-origin: admin:admin' \
		-d "<m2m:ae xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\"$room\">
		     <api>ROOM</api>
	                  <lbl>Type/room Category/room location/$room</lbl>  
		       <rr>false</rr>
		    </m2m:ae>"
	echo ""

	# Creation of the Sensors :
	for device in Brightness Temperature ; do
		curl -X POST "http://127.0.0.1:8080/~/in-cse/in-name/$room" -H 'cache-control: no-cache' -H 'content-type: application/xml;ty=3' -H 'x-m2m-origin: admin:admin' \
			-d "<m2m:cnt xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\"$device\">
			    </m2m:cnt>"
		echo ""

		case "$device" in
			Brightness)
				category="brightness"
				data="120"
				unit="Lux"
				;;
			Temperature)
				category="temperature"
				data="20"
				unit="Degree"
				;;
		esac

                curl -X POST "http://127.0.0.1:8080/~/in-cse/in-name/$room/$device" -H 'cache-control: no-cache' -H 'content-type: application/xml;ty=4' -H 'x-m2m-origin: admin:admin' \
			-d "<m2m:cin xmlns:m2m=\"http://www.onem2m.org/xml/protocols\">
				<rn>DATA</rn>
				<cnf>message</cnf>
				<con>
					&lt;obj&gt;
					        &lt;str name=&quot;location&quot; val=&quot;$room&quot;/&gt;
					        &lt;str name=&quot;category&quot; val=&quot;$category&quot;/&gt;
					        &lt;float name=&quot;data&quot; val=&quot;$data&quot;/&gt;
					        &lt;float name=&quot;unit&quot; val=&quot;$unit&quot;/&gt;
				        &lt;/obj&gt;
				</con>
			    </m2m:cin>"
	done

	# Creation of the Actuators :
	for device in Lamp Window Hvac Door Shutter ; do
		curl -X POST "http://127.0.0.1:8080/~/in-cse/in-name/$room" -H 'cache-control: no-cache' -H 'content-type: application/xml;ty=3' -H 'x-m2m-origin: admin:admin' \
			-d "<m2m:cnt xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\"$device\">
			    </m2m:cnt>"
		echo ""

		case "$device" in
			Lamp)
				category="lamp"
				state="ON"
				;;
			Window)
				category="window"
				state="ON"
				;;
			Hvac)
				category="hvac"
				state="ON"
				;;
			Door)
				category="door"
				state="ON"
				;;
			Shutter)
				category="shutter"
				state="ON"
				;;
		esac

                curl -X POST "http://127.0.0.1:8080/~/in-cse/in-name/$room/$device" -H 'cache-control: no-cache' -H 'content-type: application/xml;ty=4' -H 'x-m2m-origin: admin:admin' \
			-d "<m2m:cin xmlns:m2m=\"http://www.onem2m.org/xml/protocols\">
				<rn>DATA</rn>
				<cnf>message</cnf>
				<con>
                                        &lt;obj&gt;
	                                        &lt;str name=&quot;location&quot; val=&quot;$room&quot;/&gt;
	                                        &lt;str name=&quot;category&quot; val=&quot;$category&quot;/&gt;
	                                        &lt;str name=&quot;state&quot; val=&quot;$state&quot;/&gt;
				        &lt;/obj&gt;
				</con>
			    </m2m:cin>"
	done
done
