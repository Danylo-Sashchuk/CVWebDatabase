function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    });
}

function clickExpandAll() {
    const buttons = document.querySelectorAll('.collapse-button:not(.activated)');
    buttons.forEach(button => {
        button.click();
    });
}

function clickCollapseAll() {
    const buttons = document.querySelectorAll('.collapse-button.activated');
    buttons.forEach(button => {
        button.click();
    });
}

function assignClicksAndExpand() {
    const coll = document.getElementsByClassName("collapse-button");

    for (let i = 0; i < coll.length; i++) {
        coll[i].addEventListener("click", function () {
            this.classList.toggle("activated");
            const content = this.nextElementSibling;
            if (content.style.maxHeight) {
                content.style.maxHeight = null;
            } else {
                content.style.maxHeight = content.scrollHeight + "px";
            }
            content.addEventListener('transitionend', adjustMainContentPadding);
        });
        coll[i].click();
        adjustMainContentPadding();
    }
}

function assignTransition() {
    const collapsibleContents = document.getElementsByClassName("collapsible-content");
    for (let i = 0; i < collapsibleContents.length; i++) {
        collapsibleContents[i].style.maxHeight = collapsibleContents[i].scrollHeight + "px";
        collapsibleContents[i].style.transition = "max-height 0.2s ease-in-out";
    }
}

function adjustMainContentPadding() {
    const mainContent = document.querySelector('.form-wrapper');

    const collapsibleContents = document.getElementsByClassName('collapsible-content');

    let totalExpandedHeight = 0;
    for (let content of collapsibleContents) {
        if (content.style.maxHeight) {
            totalExpandedHeight += content.scrollHeight;
        }
    }
    mainContent.style.paddingBottom = totalExpandedHeight + 'px';
}

window.addEventListener('scroll', function () {
    var goTopButton = document.querySelector('.go-top');
    // Check if the page is scrolled down by 100px
    if (window.pageYOffset > 100) {
        goTopButton.style.display = 'block'; // Show the button
    } else {
        goTopButton.style.display = 'none'; // Hide the button
    }
});

// On page load, hide the "Go top" button
document.addEventListener('DOMContentLoaded', function () {
    var goTopButton = document.querySelector('.go-top');
    goTopButton.style.display = 'none';
});

document.addEventListener('DOMContentLoaded', function () {
    assignClicksAndExpand();
    assignTransition();
});
