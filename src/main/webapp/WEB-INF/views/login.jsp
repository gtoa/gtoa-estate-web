<html>
        <head>
                <meta name="google-signin-scope" content="profile email">
                <meta name="google-signin-client_id" content="405616143986-5ipmf76v2hmt4fei98e42qu22q65r5nf.apps.googleusercontent.com">
                <script src="https://apis.google.com/js/platform.js" async defer></script>

                <title>login from me</title>

        </head>
        <body>
                <a href="/registry">registry</a>
                <br>
                <form name="form1" action="login" method="post">
                        <table width="200" border="1">
                        <tr>
                                <td colspan="2">Login</td>
                        </tr>
                        <tr>
                                <td>Account</td>
                                <td><input type="text" name="account" size="10"></td>
                        </tr>
                        <tr>
                                <td>Password</td>
                                <td><input type="password" name="password" size="10"></td>
                        </tr>
                        <tr>
                                <td colspan="2"><input type="submit" name="submit" value="Login"></td>
                        </tr>
                        </table>
                </form>
                <script>
                        window.fbAsyncInit = function() {
                                FB.init({
                                        appId      : '1048263115193749',
                                        xfbml      : true,
                                        version    : 'v2.2'
                                });
                        };

                        (function(d, s, id){
                                var js, fjs = d.getElementsByTagName(s)[0];
                                if (d.getElementById(id)) {return;}
                                js = d.createElement(s); js.id = id;
                                js.src = "//connect.facebook.net/en_US/sdk.js";
                                fjs.parentNode.insertBefore(js, fjs);
                        }(document, 'script', 'facebook-jssdk'));


                        function statusChangeCallback(response) {
                                console.log('statusChangeCallback');
                                console.log(response);
                                if (response.status === 'connected') {
                                        fbSignIn();
                                } else if (response.status === 'not_authorized') {
                                        document.getElementById('status').innerHTML = 'Please log ' +
                                                'into this app.';
                                       // FB.login(function(response) {}, {scope: 'public_profile,email'});
                                } else {
                                        document.getElementById('status').innerHTML = 'Please log ' +
                                                'into Facebook.';
                                       // FB.login(function(response) {}, {scope: 'public_profile,email'});
                                }
                        }


                        function checkLoginState() {
                                FB.getLoginStatus(function(response) {
                                        statusChangeCallback(response);
                                });
                        }


                        function fbSignIn() {
                                console.log('Welcome!  Fetching your information.... ');
                                FB.api('/me', {fields: 'email,name'}, function(response) {
                                        console.log('Successful login for: ' + response.name);
                                        window.location.href = "/thirdreg?thirdId=" + response.id
                                        + "&thirdName=" + response.name + "&thirdEmail=" + response.email
                                         + "&thirdType=1";
//                                        document.getElementById('status').innerHTML =
//                                                'Thanks for logging in, ' + JSON.stringify(response) + '!';
                                });
                        }

                        function googleSignIn(googleUser) {
                                var profile = googleUser.getBasicProfile();
                                document.getElementById('googleUser').innerHTML =
                                        'Thanks for logging in, ' + profile + '!';
                                window.location.href = "/thirdreg?thirdId=" + profile.getId()
                                        + "&thirdName=" + profile.getName() + "&thirdEmail=" + profile.getEmail()
                                        + "&thirdType=2";
//                                console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
//                                console.log('Name: ' + profile.getName());
//                                //console.log('Image URL: ' + profile.getImageUrl());
//                                console.log('Email: ' + profile.getEmail());
                        }

                </script>

                <fb:login-button scope="public_profile,email" show-faces="true" width="200" max-row="1" onlogin="checkLoginState();">
                </fb:login-button>

                <div class="g-signin2" data-onsuccess="googleSignIn" data-theme="dark"></div>


                        <div id="status">
                </div>
        <div id="googleUser"></div>
        </body>
</html>
