
const firebaseConfig = {
    apiKey: "AIzaSyAnaDymqySA5ShLrrO9PBgk6RUGuuQgHJc",
    authDomain: "goserviceapp-69d6d.firebaseapp.com",
    databaseURL: "https://goserviceapp-69d6d-default-rtdb.firebaseio.com",
    projectId: "goserviceapp-69d6d",
    storageBucket: "goserviceapp-69d6d.appspot.com",
    messagingSenderId: "607574643159",
    appId: "1:607574643159:web:9aed7b8e3b13341f9a3b92",
    measurementId: "G-G7JD784XGR"
};

const app = firebase.initializeApp(firebaseConfig);

const storage = firebase.storage();

const progressbar = document.querySelector(".progressbar");
const img = document.querySelector(".img");
const fileData = document.querySelector(".filedata");
const urlimagem = document.querySelector("#urlimagem")
let file;
let fileName;
let uploadedFileName;

progressbar.style.display = "none";
const getImageData = (e) => {
    file = e.target.files[0];
    fileName = Date.now() + file.name;
    if (fileName) {
        fileData.style.display = "block";
    }
    fileData.innerHTML = fileName;
    console.log(file, fileName);
};

    function showSpinner(flag = true){
        if (flag) {
            progressbar.style.display = "block";
            img.style.display = "none";
        } else {
            progressbar.style.display = "none";
            img.style.display = "block";
        }
    }
const uploadImage = () => {
    showSpinner();
    const storageRef = storage.ref().child("profilePics");
    const folderRef = storageRef.child(fileName);
    const uploadtask = folderRef.put(file);
    uploadtask.on(
        "state_changed",
        (snapshot) => {
            console.log("Snapshot", snapshot.ref.name);
            uploadedFileName = snapshot.ref.name;
        },
        (error) => {
            console.log(error);
        },
        () => {
            storage
                .ref("profilePics")
                .child(uploadedFileName)
                .getDownloadURL()
                .then((url) => {
                    showSpinner(false);
                    console.log("URL", url);
                    img.setAttribute("src", url);
                    urlimagem.setAttribute("value", url);
                });
            console.log("File Uploaded Successfully");
        }
    );
};