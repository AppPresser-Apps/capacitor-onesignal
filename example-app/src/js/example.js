import { CapOneSignal } from 'capacitor--onesignal';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    CapOneSignal.echo({ value: inputValue })
}
