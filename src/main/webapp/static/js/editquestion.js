function addAnswer(){
    const answersContainer = document.getElementById("answers");
    if(answersContainer == null){
        console.log("No div with id = answers");
        return;
    }

    const answer = document.createElement("div");
    answer.innerHTML = "hello";

    answersContainer.appendChild(answer);
}