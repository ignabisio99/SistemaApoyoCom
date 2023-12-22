import { GoogleAuthProvider, signInWithPopup } from "https://www.gstatic.com/firebasejs/10.4.0/firebase-auth.js"
import { auth } from "./firebase.js";
import { showMessage } from "./showMessage.js";

const googleButton = document.querySelector("#googleLogin");

googleButton.addEventListener("click", async (e) => {
    e.preventDefault();

    const provider = new GoogleAuthProvider();
    try {
        const credentials = await signInWithPopup(auth, provider);
        const idToken = await credentials.user.getIdToken();
        const usuario={
            email: credentials.user.email,
            token: idToken
        }
        const requestOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                // Puedes agregar más encabezados según sea necesario, como tokens de autorización, etc.
            },
            body: JSON.stringify(usuario),
        };
        fetch("http://localhost:4567/login/google", requestOptions)
            .then(response => {
                if (!response.ok) {
                    //throw new Error('Error en la respuesta del servidor');
                    showMessage("Algo salió mal", "error")
                }
                return response.text();
            })
            .then(data => {
                // Manejar la respuesta del servidor
                console.log('Respuesta del servidor:', data);
                window.location.href = 'http://localhost:4567/perfil';

            }).catch(error => {
            showMessage("Error en el servidor", "error")
        });

        showMessage("Bienvenido " + credentials.user.displayName,"success");
    } catch (error) {
        console.log(error);
    }
});