# Quick Start server

## 1. Configure the Server

To use MWV, you need to include the following plugins in your code:

```pawn
#include <Pawn.RakNet>
#include <json>
#include <gvar>
#include <SMWV>
```

!!! warning
    You must set `mwv_packet_id` to the same value you set in jni
    ```pawn
    #define mwv_packet_id 253
    ```

## 2. Registering Events

You must register the events you want to use on your server. This should be done in the **OnGameModeInit()** function:

```pawn
public OnGameModeInit()
{
    // Register events
    MwvRegisterEvent("alert_response", "OnAlertResponse");
    MwvRegisterEvent("show_phone", "OnPhoneResponse");
    return 1;
}
```

## 3. Initializing the Browser for the Player

In the **OnPlayerConnect** event, you should initialize the browser for the player. Here's an example:

```pawn
public OnPlayerConnect(playerid)
{
    // Initialize the browser with the desired URL
    MwvInitBrowser(playerid, "http://192.168.0.106:3000");
    MwvSetBrowserFocus(playerid, false);
    SpawnPlayer(playerid); 
    
    return 1;
}
```

## 4. Sending Events to the Browser

To send events to the browser, use the `MwvSendEvent` function. Here's an example of how to send an event when a player uses a command:

```pawn
if (!strcmp(cmdtext, "/mwv")){
    new Node:event_data_node = JSON_Object(
        "id", JSON_Int(ALERT_SET_SKIN),
        "title", JSON_String("Set Skin"),
        "body", JSON_String("Enter the skin ID")
    );
    
    // Send the event to the browser
    MwvSendEvent(playerid, "create_dialog", event_data);
    return 1;
}
```

## 5. Receiving Events from the Browser

To receive events, you need to create a `forward` function with the same name as the registered callback. Below is an example of how to handle the response from an alert:

```pawn
forward OnAlertResponse(playerid, event_data[]);
public OnAlertResponse(playerid, event_data[])
{
    MwvSetBrowserFocus(playerid, false);

    new Node:event_data_node;
    JSON_Parse(event_data, event_data_node);

    new id, skin_id;
    new bool:result;
    
    JSON_GetInt(event_data_node, "id", id);
    JSON_GetInt(event_data_node, "skin_id", skin_id);
    JSON_GetBool(event_data_node, "result", result);
  
    switch (id)
    {
        case ALERT_SET_SKIN:
        {
            if (!result)
                return 1;

            SetPlayerSkin(playerid, skin_id);
            SendClientMessage(playerid, -1, "[MWV] Skin set: %d", skin_id);
        }
    }

    return 1;
}
```
