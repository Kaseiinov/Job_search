$(document).ready(function (){
    const aboutMeCard = $('.about_me');
    const educationCard = $('.education');
    const contactCard = $('.contacts');

    aboutMeCard.hide();
    educationCard.hide();
    contactCard.hide();

    let buttonAboutMe = $('#aboutMe');
    buttonAboutMe.click(() => {
        aboutMeCard.show();
    })

    let buttonEducation = $('#educationButton');
    buttonEducation.click(() => {
        educationCard.show();
    })

    let buttonContact = $('#contactButton');
    buttonContact.click(() => {
        contactCard.show();
    })
})

