// Import the functions you need from the SDKs you need
import { initializeApp } from "https://www.gstatic.com/firebasejs/10.4.0/firebase-app.js";
import { getAuth } from "https://www.gstatic.com/firebasejs/10.4.0/firebase-auth.js"
// https://firebase.google.com/docs/web/setup#available-libraries


// Your web app's Firebase configuration
const firebaseConfig = {
    apiKey: "AIzaSyDV4Um8wTm6MwVKAi7SlRnnF0JebSUmZU4",
    authDomain: "tpdds2023-48514.firebaseapp.com",
    projectId: "tpdds2023-48514",
    storageBucket: "tpdds2023-48514",
    messagingSenderId: "675377333407",
    appId: "1:675377333407:web:1603d93d4ad43712f24eaa"
};
// Initialize Firebase
export const app = initializeApp(firebaseConfig);
export const auth = getAuth(app)