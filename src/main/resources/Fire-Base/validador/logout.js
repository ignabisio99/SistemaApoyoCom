import { signOut } from "https://www.gstatic.com/firebasejs/10.4.0/firebase-auth.js";
import { auth } from "./firebase.js";
import { showMessage } from "./showMessage.js";

export async function logout() {

        try {
            //se cierra la sesion en firebase
            await signOut(auth)
            //elimino la sesion en el backend

            // obtengo id de sesion del local storage
            var id_sesion = localStorage.getItem("idSesion")

            // hago post a la api
            const requestOptions = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    // Puedes agregar más encabezados según sea necesario, como tokens de autorización, etc.
                },
                body: JSON.stringify(id_sesion),
            };

            fetch("http://localhost:4567/api/logout", requestOptions)
                .then(response => {
                    if (!response.ok) {
                        showMessage("Algo salió mal", "error")
                        throw new Error('Error HTTP: ' + response.status);
                    }
                    // eliminio el id del local storage
                    localStorage.removeItem("idSesion")
                    console.log('el usuario cerró sesión')
                    //me lleva
                    window.location.href="http://localhost:4567/login"
                    })
                .catch(error => {
                showMessage("Error en la solicitud", "error")
            });
        } catch (error) {
            console.log(error)
            showMessage("Algo salió mal", "error")
        }
}