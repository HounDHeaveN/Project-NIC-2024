package com.projectapi.Project_NIC.service;

import com.projectapi.Project_NIC.model.ArchiveDocument;
import com.projectapi.Project_NIC.model.ClientDocument;

import com.projectapi.Project_NIC.model.Review;
import com.projectapi.Project_NIC.repository.ArchiveRepository;
import com.projectapi.Project_NIC.repository.DocumentRepository;
import com.projectapi.Project_NIC.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.*;
import java.util.logging.Logger;

@Service
public class DocumentService {

    @Autowired
    private final DocumentRepository documentRepository;
    private final MongoTemplate mongoTemplate;
    @Autowired
    private ReviewRepository reviewRepository;
    private static final Logger LOGGER = Logger.getLogger(DocumentService.class.getName());

    @Autowired
    public DocumentService(DocumentRepository documentRepository, MongoTemplate mongoTemplate) {
        this.documentRepository = documentRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Autowired
    public ArchiveRepository archiveRepository;

    public UUID saveDocument(ClientDocument document) {
        document.setDocument_id(UUID.randomUUID());

        Date date = new Date();
        document.setCreated_on(date);

        documentRepository.save(document);
        return document.getDocument_id();
    }

    public ResponseEntity<ClientDocument> getDocumentById(UUID documentId) {
        System.out.println("searching for document id: " + documentId);
        ClientDocument document = documentRepository.findById(documentId).orElse(null);

        System.out.println("Document found : " + document);
        return ResponseEntity.ok(document);

    }

    public List<ClientDocument> getDocumentsByPersonId(int personId) {
        System.out.println("searching for document with personId: " + personId);
        return documentRepository.findByPersonId(personId);

    }

    public Review saveOrUpdateReview(Review review) {
        Optional<Review> existingReview = reviewRepository.findByApplicationTransactionId(String.valueOf(review.getApplicationTransactionId()));

        if (existingReview.isPresent()) {
            Review existing = existingReview.get();
            existing.setReview(review.getReview());

            return reviewRepository.save(existing);
        }

        return reviewRepository.save(review);
    }

    public ArchiveDocument archiveDocument(ArchiveDocument archiveDocument) {
        Optional<ArchiveDocument> existingArchive = archiveRepository.findByApplicationTransactionId(Long.valueOf(archiveDocument.getApplicationTransactionId()));

        if (existingArchive.isPresent()) {
            ArchiveDocument archivedoc = existingArchive.get();
            archivedoc.setArchival_comments(archiveDocument.getArchival_comments());

            return archiveRepository.save(archivedoc);

        }

        return archiveRepository.save(archiveDocument);
    }


    public String deleteDocument(UUID documentId) {
        ClientDocument document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        mongoTemplate.save(document, "archive_documents");
        LOGGER.info("Document archived successfully with ID: " + documentId);

        documentRepository.deleteById(documentId);
        LOGGER.info("Document deleted successfully with ID: " + documentId);

        return "Document archived successfully";
    }
}