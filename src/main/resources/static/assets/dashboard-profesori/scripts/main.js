$(document).ready(function () {
    $('.menu').click(function () {
        $('.responsive-menu').toggleClass('toggle');
    });

    var btn = document.getElementById("logOutBtn");
    btn.onclick = () => {
        $.ajax({
            type: "POST",
            url: "/sign-out",
            success: data => {
                if (data === "valid") {
                    window.location.href = "/";
                }
            }
        })
    };
});