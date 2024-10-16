# Installation

The Smwv module is published to the Maven Central Repository.

## Smwv client

### Smwv is divided into two, you need to include the dependencies and [static library](http://127.0.0.1:8000/Smwv/getting-started/installation/#adding-the-static-library) in your jni

##### Add dependency:

=== "Groovy"

    ``` groovy
    implementation "io.github.ruan625br:smwv:<version>"
    ```

=== "Kotlin"

    ``` kotlin
    implementation("io.github.ruan625br:smwv:<version>")
    ```
=== "Gradle Version Catalog"

    ``` toml
    [versions]
    smwv = "<version>"

    [libraries]
    smwv = { module = "io.github.ruan625br:smwv", version.ref = "smwv" }
    ```
    #### build.gradle.kts
    ``` kotlin
    implementation(libs.smwv)
    ```

### Adding the static library

1. Download the static library from the [release page](https://github.com/Ruan625Br/Smwv/releases)
2. Extract the file and copy the `mwv` folder to: **/your_jni/vendor/**.
3. Open the `Android.mk` file and include `libMwv`:
``` mk
    ...
    
    # libMwv
    include $(CLEAR_VARS)
    LOCAL_MODULE := libMwv
    LOCAL_SRC_FILES := vendor/mwv/libMwv.a
    include $(PREBUILT_STATIC_LIBRARY)
    
    ...
    LOCAL_STATIC_LIBRARIES := libMwv
```

### Setup the static library

Open the file **/your_jni/net/netgame.cpp**
Add the following code:
``` cpp
    ...
    #include "vendor/mwv/Mwv.h"
     
    ...
    CNetGame::CNetGame(const char* szHostOrIp, int iPort, const char* szPlayerName, const char* szPass)
    {
      ...
      m_pRakClient = RakNetworkFactory::GetRakClientInterface();
	  if(!m_pRakClient) 
	  {
	     Log("[err:NetGame]: m_pRakClient doesn't inited. Wtf?");
		 exit(0);
	  }
	  
	   mwv::initializeNetwork(m_pRakClient, ID_JUAN); //change ID_JUAN to another packet Id
      ...
    }
    
    
   ...
   void CNetGame::UpdateNetwork()
   {
      ...
      switch(packetIdentifier)
		{
		 ...
		   case ID_JUAN: // you packet Id
                mwv::processIncomingPacket(pkt);
                break;
		}
   
   }		
``` 

!!! warning
      The `ID_JUAN` is your packet id, check the availability in **PacketEnumeration** and create one

## Smwv server

1. Download the `SMWV.inc` file from the [release page](https://github.com/Ruan625Br/Smwv/releases)
2. Copy to **/your_server/pawno/include/**
3. Install the following plugins:
    - [RakNet](https://github.com/katursis/Pawn.RakNet)
    - [GVar](https://github.com/samp-incognito/samp-gvar-plugin)
    - [json](https://github.com/Southclaws/pawn-json)