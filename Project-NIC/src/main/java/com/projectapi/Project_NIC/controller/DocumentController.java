package com.projectapi.Project_NIC.controller;

import com.projectapi.Project_NIC.model.ArchiveDocument;
import com.projectapi.Project_NIC.model.ClientDocument;
import com.projectapi.Project_NIC.model.Review;
import com.projectapi.Project_NIC.model.WatermarkRequest;
import com.projectapi.Project_NIC.repository.DocumentRepository;
import com.projectapi.Project_NIC.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class DocumentController {
    private final DocumentService documentService;

    @Autowired
    private  DocumentRepository documentRepository;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/savedocument")
    public ResponseEntity<UUID> saveDocument(@RequestBody ClientDocument document) {
        UUID documentId = documentService.saveDocument(document);
        return ResponseEntity.ok(documentId);
    }


    @GetMapping("/getdocument/{id}")
    public ResponseEntity<ClientDocument> getDocument(@PathVariable("id") UUID document_id){
        System.out.println("received request for document ID: " + document_id);
        ResponseEntity<ClientDocument> document = documentService.getDocumentById(document_id);

        return ResponseEntity.ok(document.getBody());
    }

    @GetMapping("/documentof/{personId}")
    public ResponseEntity<List<ClientDocument>> getDocumentsByPersonId(@PathVariable("personId") int personId) {
        List<ClientDocument> documents = documentService.getDocumentsByPersonId(personId);

        if (documents.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(documents);
        }
    }

    @PostMapping("/reviewdocument")
    public ResponseEntity<?> saveOrUpdateReview(@RequestBody Review review) {
        Optional<ClientDocument> clientdocumentOptional = documentRepository.findByApplicationTransactionId(review.getApplicationTransactionId());

        if (clientdocumentOptional.isPresent()) {
            review.setApplicationTransactionId(clientdocumentOptional.get().getFile_information().getApplication_transaction_id());
            Review savedReview = documentService.saveOrUpdateReview(review);

            return ResponseEntity.ok(savedReview);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/archivedocument")
    public ResponseEntity<?> archiveDocument(@RequestBody ArchiveDocument archiveDocument) {
        Optional<ClientDocument> clientDocumentOptional = documentRepository.findByApplicationTransactionId(archiveDocument.getApplicationTransactionId());

        if (clientDocumentOptional.isPresent()) {
            ArchiveDocument savedArchiveDocument = documentService.archiveDocument(archiveDocument);
            return ResponseEntity.ok(savedArchiveDocument);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addwatermarktodocument")
    public ResponseEntity<?> addWatermarkToDocument(@RequestBody WatermarkRequest watermarkRequest){
        try {
            ClientDocument updatedDocument = documentService.addWatermarkToDocument(watermarkRequest.getApplicationTransactionId(),
                    watermarkRequest.getWatermark());
            return new ResponseEntity<>(updatedDocument, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/viewreviewlog/{applicationTransactionId}")
    public ResponseEntity<Review> getReviewByApplicationId(@PathVariable long applicationTransactionId) {
        Optional<Review> reviewOptional = documentService.getReviewByApplicationTransactionId(applicationTransactionId);

        return reviewOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/vieweditlog/{applicationTransactionId}")
    public ResponseEntity<ArchiveDocument> getArchiveDocumentByApplicationTransactionId(@PathVariable long applicationTransactionId) {
        Optional<ArchiveDocument> archiveDocumentOptional = documentService.getArchiveDocumentByApplicationTransactionId(applicationTransactionId);

        return archiveDocumentOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}

