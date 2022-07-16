import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IEtatDemande, EtatDemande } from '../etat-demande.model';
import { EtatDemandeService } from '../service/etat-demande.service';

import { EtatDemandeRoutingResolveService } from './etat-demande-routing-resolve.service';

describe('EtatDemande routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EtatDemandeRoutingResolveService;
  let service: EtatDemandeService;
  let resultEtatDemande: IEtatDemande | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(EtatDemandeRoutingResolveService);
    service = TestBed.inject(EtatDemandeService);
    resultEtatDemande = undefined;
  });

  describe('resolve', () => {
    it('should return IEtatDemande returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEtatDemande = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEtatDemande).toEqual({ id: 123 });
    });

    it('should return new IEtatDemande if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEtatDemande = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEtatDemande).toEqual(new EtatDemande());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as EtatDemande })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEtatDemande = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEtatDemande).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
