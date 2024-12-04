<?php
require_once __DIR__ . '/routes.php';  // Pour avoir accès à la fonction connectToDatabase()

header('Content-Type: application/json');
$target_dir = "uploads/";
$target_file = $target_dir . basename($_FILES["image"]["name"]);
$uploadOk = 1;
$imageFileType = strtolower(pathinfo($target_file,PATHINFO_EXTENSION));
$response = ['success' => false, 'message' => '', 'image_id' => null];


// Check if image file is a actual image or fake image
if(isset($_POST["submit"])) {
  $check = getimagesize($_FILES["fileToUpload"]["tmp_name"]);
  if($check !== false) {
    $response['message'] = "File is an image - " . $check["mime"] . ".";
    $uploadOk = 1;
  } else {
    $response['message'] = "File is not an image.";
    $uploadOk = 0;
  }
}

// Check file size
if ($_FILES["fileToUpload"]["size"] > 500000) {
  $response['message'] = "Sorry, your file is too large.";
  $uploadOk = 0;
}

// Allow certain file formats
if($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg" && $imageFileType != "gif" ) {
  $response['message'] = "Sorry, only JPG, JPEG, PNG & GIF files are allowed.";
  $uploadOk = 0;
}

// Check if $uploadOk is set to 0 by an error
if ($uploadOk != 0) {
    if (move_uploaded_file($_FILES["image"]["tmp_name"], $target_file)) {
        $response['success'] = true;
        $response['message'] = "The file ". htmlspecialchars(basename($_FILES["image"]["name"])). " has been uploaded.";
       
        // Insérer les informations de l'image dans la base de données
        try {
            $pdo = connectToDatabase();
           
            $fileName = basename($_FILES["image"]["name"]);
            $fileType = $imageFileType;
            $uploadDate = date('Y-m-d H:i:s');
            $fileSize = $_FILES["image"]["size"];
           
            // Lire le contenu du fichier
            $imageData = file_get_contents($target_file);
           
            $stmt = $pdo->prepare("INSERT INTO Images (FileName, FileType, UploadDate, FileSize, ImageData) VALUES (?, ?, ?, ?, ?)");
            $stmt->bindParam(1, $fileName);
            $stmt->bindParam(2, $fileType);
            $stmt->bindParam(3, $uploadDate);
            $stmt->bindParam(4, $fileSize);
            $stmt->bindParam(5, $imageData, PDO::PARAM_LOB);
           
            if ($stmt->execute()) {
                $response['image_id'] = $pdo->lastInsertId();
                $response['message'] .= " Image information saved to database.";
            } else {
                $response['message'] .= " But failed to save image information to database.";
            }
        } catch (PDOException $e) {
            $response['message'] .= " Database error: " . $e->getMessage();
        }
    } else {
        $response['message'] = "Sorry, there was an error uploading your file.";
    }
}

echo json_encode($response);
?>