import { signInWithEmailAndPassword } from "https://www.gstatic.com/firebasejs/10.4.0/firebase-auth.js"
import { auth } from "./firebase.js";
import { showMessage } from "./showMessage.js";

const signInForm = document.querySelector("#loginForm");

signInForm.addEventListener("submit",async (e) => {
    e.preventDefault();
    const email = signInForm["mailIngresado"].value;
    const password = signInForm["passwordIngresada"].value;


    try {
        //me autentico con firebase
        const userCredentials = await signInWithEmailAndPassword(auth, email, password)
        //console.log(JSON.stringify(userCredentials))
        // reset the form
        //signInForm.reset();
        const idToken = await userCredentials.user.getIdToken();
        const credentials = {
            email: email,
            password: password,
            token: idToken
        };
        console.log(credentials);

        //Envio email y contraseña al backend y me devuelve el id de la sesion
        const requestOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                // Puedes agregar más encabezados según sea necesario, como tokens de autorización, etc.
            },
            body: JSON.stringify(credentials),
        };

        fetch("http://localhost:4567/loginLiv", requestOptions)
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

       /* //mostrar la bienvenida cuando inicie sesion
        window.onload = function () {
                showMessage("Bienvenido " + userCredentials.user.email, "success")
        };
        */

    } catch (error) {
        console.log(error)
        if (error.code == 'auth/invalid-login-credentials') {
            showMessage("Correo y/o contraseña inválidos", "error")
        } else if (error.code == 'auth/invalid-email') {
            showMessage("Correo electrónico inválido", "error")
        } else if (error.code =='auth/missing-password') {
            showMessage("Debe ingresar una contraseña","error")
        } else {
            showMessage("Algo salió mal", "error")
        }
    }
});

