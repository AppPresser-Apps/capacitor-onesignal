import { CapOneSignal } from 'capacitor-onesignal';

const initializeOneSignal = async () => {
    try {
        await CapOneSignal.initialize({ appId: '739ae760-827d-4c47-a43e-27f8c6b73701' });
    } catch (e) {
        console.error("Error initializing OneSignal:", e);
    }
};

window.addEventListener('DOMContentLoaded', initializeOneSignal);

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    CapOneSignal.echo({ value: inputValue })
}
