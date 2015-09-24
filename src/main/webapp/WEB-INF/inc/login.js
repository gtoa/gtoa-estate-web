!function(o) {
    function n() {
        o(".img-box").css("height", o(window).height() + "px")
    }
    function i(n, i) {
        var t=!0;
        return "" == i && (o.dialog("Password can not be empty"), t=!1), "" == n && (o.dialog("Account can not be empty"), t=!1), t
    }
    function t() {
        var n = o.trim(o(".account").val()), t = o.trim(o(".password").val()), e = {
            userName: n,
            password: t
        };
        i(n, t) && o.post("http://localhost:8080/login", e, function(n) {
            var i = JSON.parse(n);
            0 == i.code ? location.href = "http://localhost:8080" : o.dialog("Login failed")
        })
    }
    function e() {
        o(".login").animate({
            top: "50%"
        }, 700), o(".account").focus(), o("input").on("focus", function() {
            o(this).addClass("active")
        }).on("blur", function() {
            o(this).removeClass("active")
        }), o(".login-btn").on("click", function() {
            t()
        }), o(document).on("keyup", function(o) {
            13 == o.keyCode && t()
        }), o(window).resize(function() {
            n()
        })
    }
    o.dialog = function(n) {
        o(".ui-dialog").length && o(".ui-dialog").remove();
        var i = '<div class="ui-dialog">' + n + "</div>";
        o("body").append(i), setTimeout(function() {
            o(".ui-dialog").remove()
        }, 1500)
    }, function() {
        n(), e()
    }()
}(jQuery);

