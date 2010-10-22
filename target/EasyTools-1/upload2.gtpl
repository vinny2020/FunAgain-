<html>
    <body>
        <h1>Please upload a text file</h1>
        <form action="/uploadText.groovy" 
                method="post" enctype="multipart/form-data">
            <input type="file" name="myTextFile">
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
