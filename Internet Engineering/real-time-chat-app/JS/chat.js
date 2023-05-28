const form = document.querySelector(".typing-area"),
inputField = form.querySelector(".input-field")
sendBtn = form.querySelector("button"),
chatBox = document.querySelector(".chat-box");

form.onsubmit = (e)=>{
    e.preventDefault();
}


chatBox.onmouseenter = () => {
    chatBox.classList.add("active")
}
chatBox.onmouseleave = () => {
    chatBox.classList.remove("active")
}

sendBtn.onclick = () => {
    let xhr = new XMLHttpRequest();
    xhr.open("POST", "php/insert-chat.php", true);
    xhr.onload = ()=>{
      if(xhr.readyState === XMLHttpRequest.DONE){
          if(xhr.status === 200){
            inputField.value = "";
            if (!chatBox.classList.contains("active")) {
                chatBox.scrollTo = chatBox.scrollHeight;
            }            
          }
      }
    }
    let formData = new FormData(form);
    xhr.send(formData);
}

setInterval( () => {
    let xhr = new XMLHttpRequest();
    xhr.open("POST", "php/get-chat.php", true);
    xhr.onload = ()=>{
      if(xhr.readyState === XMLHttpRequest.DONE){
          if(xhr.status === 200){
            let data = xhr.response;
            chatBox.innerHTML = data;
            if (!chatBox.classList.contains("active")) {
                chatBox.scrollTo = chatBox.scrollHeight;
            } 
          }
      }
    }
    let formData = new FormData(form);
    xhr.send(formData);
}, 500)

