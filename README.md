# gateway-load-example

Gateway is some kind of a proxy between servers and clients. Let's name servers as channel masters.  
So, main idea is to put gateway somewhere at hosting or even at home with white IP address, and than this gateway can be used for communication between clients and servers.  
It allows easy way to test server, add one more server(even from another location) and easy way to update it.
![gateway's picture](docs/gateway.png)

For new at my machine gateway may pass through itself more then milliion messages per second, so it could be a good start for any kind of applicaitons. I think shooter will work fine which it.

The main thing about gateway - i am using kotlin to produce an applications. For me it is an entertaiment and studying. 
I can't publish gateway for now, coz it is as early developing stage, but it is workable and can be used if required. I will update it when i will need new features for my applications.
There are couple features which are required for production, thins i am keeping in mind: 
- channel master authorization configuration
- encryption(optional)
- embedded reconnect for clients and channel masters
- round robin mode for channel masters
- some support for versions. So inside channel could be different version, can be useful for migration to new server. But also can be done by naming new channel for new version
- cluster mode - maybe one day, but it is not an aim.

This project is just a copy of a project i have at my own gitlab server. That's why there is no log/branches and so on. Maybe in future this project and gateway too will be migrated into github, coz it is just a service applications
