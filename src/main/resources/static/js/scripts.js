// Task-1

let array = ['string-3', 'string-5', 'string-2', 'string-6', 'string-4', 'string-1']

for (let i = 0; i < array.length; i++) {
    let element = document.getElementById(array[i]).textContent
    console.log(element);
}

// Task-2

const elements = document.getElementsByClassName('element')

for (let i = 0; i < elements.length; i++){
    if(i < 3){
        elements[i].style.color = 'red'
    } else{
        elements[i].style.color = 'green'
    }
}

// Task-3

const container = document.querySelector('.container');

for (let i = 0; i < 5; i++) {
    let element = document.createElement('div');
    element.innerHTML = `Element ${i+1}`;
    element.classList.add('element');
    container.appendChild(element);
}