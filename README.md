# BankId_SeP

Project to see how Service Provider ("SeP") side of BankID works and to see how much work it involves.

Also my first attempt to work with OAuth

Uses Java Spring with Thymeleaf for basic web pages mockup and Spring Security for OAuth

## Flow

### First page
/sep/main - start of the flow, offers option to use BankIDas a new client

### BankID authentication

- After clicking on the BankID option, authentication request is built and client is redirected to BankID list of banks
- After selecting mock bank, he logs in with it
- Confirm that he agrees with passing all the data

### Application form

At this point we receive token which we use to request client data (using **/profile** with full AML compliant scope), which we then display



## Full output example

```yaml
{
    sub: 1ec7c063-d600-4961-8ea5-7a407dcc8525
    txn: 197e84b8-65f7-4bae-86eb-911e0f20223e
    verifiedClaims: class VerifiedClaims {
        verification: class Verification {
            trustFramework: cz_aml
            time: 2021-10-26T19:25:09Z
            verificationProcess: 45244782
        }
        claims: class Claims {
        }
    }
    titlePrefix: null
    titleSuffix: null
    givenName: Jan
    familyName: Novák
    middleName: null
    gender: male
    birthdate: null
    birthnumber: 7008018556
    age: null
    majority: null
    dateOfDeath: null
    birthplace: Praha 4
    birthcountry: CZ
    primaryNationality: AT
    nationalities: class Nationalities {
        [AT, CZ]
    }
    maritalstatus: MARRIED
    addresses: class Addresses {
        [class Address {
            type: PERMANENT_RESIDENCE
            street: Havlíčkova
            buildingapartment: 1064
            streetnumber: 3
            city: Kladno 3
            zipcode: 27203
            country: CZ
            ruianReference: 18676
        }, class Address {
            type: SECONDARY_RESIDENCE
            street: Bezručova
            buildingapartment: 1215
            streetnumber: 33
            city: Dolní Studénky
            zipcode: 78820
            country: CZ
            ruianReference: 11632
        }]
    }
    idcards: class Idcards {
        [class Idcard {
            type: ID
            description: Občanský průkaz
            country: CZ
            number: 123456789
            validTo: 2023-10-11
            issuer: Úřad městské části Praha 4
            issueDate: 2013-10-10
        }]
    }
    email: J.novak@email.com
    phoneNumber: +420123456789
    pep: null
    limitedLegalCapacity: null
    paymentAccounts: class PaymentAccounts {
        [CZ3650514812229966227653, CZ6550511833147245714362]
    }
    updatedAt: null
}
```

