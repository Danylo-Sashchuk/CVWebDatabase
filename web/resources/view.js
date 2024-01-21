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
    setInitialHeight();
});

document.addEventListener('DOMContentLoaded', function () {
    // Function to remove a period
    function removePeriod(event) {
        // Find the closest ancestor 'period' div relative to the clicked 'Remove period' button
        // Since the button is not inside .period, we first find .button-row, then the preceding sibling .period
        const buttonRow = event.target.closest('.button-row');
        let periodElement = null;
        // The actual .period div is two elements back, before .period-description
        periodElement = buttonRow.previousElementSibling;

        // Ensure the element exists before removing it
        periodElement.remove(); // Remove .period
        buttonRow.remove(); // Remove .button-row itself
    }

    // Attach the click event listener to the 'company-container' for event delegation
    document.querySelector('.company-container').addEventListener('click', function (event) {
        // Check if the clicked element is a 'Remove period' button
        if (event.target.classList.contains('remove-period-button')) {
            removePeriod(event);
        }
    });
});

document.addEventListener('DOMContentLoaded', function () {
    // Function to add a new period
    function addNewPeriod(event) {
        const currentButtonRow = event.target.closest('.button-row');
        const periodsContainer = event.target.closest('.company').querySelector('.periods-container');
        const companyCounter = event.target.closest('.company').getAttribute('data-company-index');
        const allPeriods = periodsContainer.querySelectorAll('.period');
        const periodCounter = allPeriods.length; // This may need to be adjusted based on how you want to count periods

        // Create the new period element
        const newPeriodDiv = document.createElement('div');
        newPeriodDiv.className = 'period';
        newPeriodDiv.innerHTML = `
      <div class="period-title">
        <input type="text" name="period-title${companyCounter}${periodCounter}" placeholder="Period Title">
      </div>
      <div class="period-time">
        <input type="month" name="period-time-start${companyCounter}${periodCounter}">
        -
        <input type="month" name="period-time-end${companyCounter}${periodCounter}">
      </div>
      <div class="period-description">
        <input type="text" name="period-description${companyCounter}${periodCounter}" placeholder="Period Description">
      </div>
    `;

        // Create the button row for the new period
        const buttonRowDiv = document.createElement('div');
        buttonRowDiv.className = 'button-row';
        buttonRowDiv.innerHTML = `
      <div class="remove-period-button-container">
        <button type="button" class="remove-period-button">Remove period</button>
      </div>
      <div class="add-period-button-container">
        <button type="button" class="add-period-button">Add new period</button>
      </div>
    `;

        // Append the button row to the new period element
        // Insert the new period after the current button row

        currentButtonRow.after(newPeriodDiv);
        newPeriodDiv.after(buttonRowDiv);
    }

    // Event delegation for handling dynamically added "Add new period" buttons
    document.querySelector('.company-container').addEventListener('click', function (event) {
        if (event.target.classList.contains('add-period-button')) {
            addNewPeriod(event);
        }
    });

    // Existing removePeriod function from earlier
});

function setInitialHeight() {
    var formWrapper = document.querySelector('.form-wrapper');

    var panels = formWrapper.querySelectorAll('.panel');

    var totalHeight = 0;

    panels.forEach(function (panel) {
        totalHeight += panel.offsetHeight;
    });

    if (totalHeight < 768) {
        formWrapper.style.height = totalHeight + 'px';
    }
}
