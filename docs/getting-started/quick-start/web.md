## Quick Start Web

Please see the [template repository](https://github.com/Ruan625Br/Smwv/smwv-template), which can be used to kick-start your project.

There are several ways to register your events:

### 1. Registering and Creating Events Manually

You can manually register events using `registerEventCallback: (eventName, callbackName) => void;`:

````ts
if (window.Mwv) {
    window.Mwv.registerEventCallback("update_speed", "updateSpeed");
    window.Mwv.registerEventCallback("show_phone", "showPhone");
}
````

You must then create functions with the callback names in `global.d.ts`:
````ts
export {};

declare global {
    interface Window {
        // your events
        updateSpeed: (data: string) => void;
        showPhone: (data: string) => void;

        // do not change
        handleMwvEvent: (eventName: string, data: string) => void;
        Mwv: {
            registerEventCallback: (eventName: string, callbackName: string) => void;
            sendEvent: (eventName: string, eventData: string) => void;
        };
    }
}
````

!!! info
    The `data` parameter is required.

You can use the registered events like this:
````ts
function App() {
    const [speed, setSpeed] = useState(0);
    const [showPhone, setShowPhone] = useState(false);

    useEffect(() => {
        window.updateSpeed = (data) => {
            const jsonData = JSON.parse(data);
            const newSpeed = parseFloat(jsonData.speed);
            setSpeed(newSpeed);
        };

        window.showPhone = () => {
            setShowPhone(true);
        };
    }, [speed, showPhone]);
}
````

### 2. Intercepting Events

Another approach is to intercept the events directly. Here's an example:
````ts
function App() {
    const [speed, setSpeed] = useState(0);
    const [showPhone, setShowPhone] = useState(false);

    useEffect(() => {
        window.handleMwvEvent = (eventName, data) => {
            switch (eventName) {
                case "update_speed": {
                    const jsonData = JSON.parse(data);
                    const newSpeed = parseFloat(jsonData.speed);
                    setSpeed(newSpeed);
                    break;
                }
                case "show_phone": {
                    setShowPhone(true);
                    break;
                }
                default:
                    break;
            }
        };
    }, [speed, showPhone]);
}
````

### 3. Recommended Way

Of all the methods mentioned above, this is the approach I (Juan) recommend the most. Let Mwv handle the event registration for you:
````ts
import { onMwvEvent } from './mwv';

function App() {
    const [speed, setSpeed] = useState(0);
    const [showPhone, setShowPhone] = useState(false);

    useEffect(() => {
        onMwvEvent("update_speed", data => {
            const jsonData = JSON.parse(data);
            const newSpeed = parseFloat(jsonData.speed);
            setSpeed(newSpeed);
        });

        onMwvEvent("show_phone", () => {
            setShowPhone(true);
        });

        // You can register multiple functions for the same event
        onMwvEvent("show_phone", () => {
            // do something else
            console.log("Another action for show_phone event");
        });
    }, [speed, showPhone]);
}
````

One of the benefits of using `onMwvEvent` is that you can register multiple callbacks for the same event. For example, if you need to run several different functions when the `show_phone` event is triggered, you can easily do so by adding another `onMwvEvent("show_phone", () => { /* code */ });`. This allows for greater flexibility in handling event-driven logic.
