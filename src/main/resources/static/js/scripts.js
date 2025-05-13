// Task-1

let array = ['string-3', 'string-5', 'string-2', 'string-6', 'string-4', 'string-1']

for (let i = 0; i < array.length; i++) {
    let element = document.getElementById(array[i]).textContent
    console.log(element);
}

