package com.intuitcraft.businessprofilemanagement.dto;

import com.intuitcraft.businessprofilemanagement.enums.RevisionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidateProfileUpdateResponse {
    private String productId;
    private RevisionStatus revisionStatus;
    private String message;

    public String getRevisionMessage() {
        return productId + " : " + revisionStatus.name() + " : " + message;
    }
}
